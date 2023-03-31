package log;

import java.util.ArrayList;
import java.util.Collections;

///**
// * Что починить:
// * 1. Этот класс порождает утечку ресурсов (связанные слушатели оказываются
// * удерживаемыми в памяти)
// * 2. Этот класс хранит активные сообщения лога, но в такой реализации он
// * их лишь накапливает. Надо же, чтобы количество сообщений в логе было ограничено
// * величиной m_iQueueLength (т.е. реально нужна очередь сообщений
// * ограниченного размера)
// */

/**
 * Как починил:
 * 1. Добавил Object lockLog, он нужен для синхронизации доступа к списку
 * слушателей и списку сообщений, чтобы не было утечек ресурсов и конкуренций за доступ
 * к общему ресурсу
 * 2. В методе append при достижении максимального количества логов старые логи удаляются
 * 3. Все методы теперь синхронизированны с lockLog
 */


public class LogWindowSource {
    private int m_iQueueLength;

    private ArrayList<LogEntry> m_messages;
    private final ArrayList<LogChangeListener> m_listeners;
    private volatile LogChangeListener[] m_activeListeners;
    private final Object lockLog = new Object();


    public LogWindowSource(int iQueueLength) {
        m_iQueueLength = iQueueLength;
        m_messages = new ArrayList<>(iQueueLength);
        m_listeners = new ArrayList<>();
    }

    public void registerListener(LogChangeListener listener) {
        synchronized (lockLog) {
            m_listeners.add(listener);
        }
    }

    public void unregisterListener(LogChangeListener listener) {
        synchronized (lockLog) {
            m_listeners.remove(listener);
        }
    }

    public void append(LogLevel logLevel, String strMessage) {
        LogEntry entry = new LogEntry(logLevel, strMessage);
        synchronized (lockLog) {
            if (m_messages.size() >= m_iQueueLength) {
                m_messages.remove(0);
            }
            m_messages.add(entry);
            for (LogChangeListener listener : m_listeners) {
                listener.onLogChanged();
            }
        }
    }

    public int size() {
        synchronized (lockLog) {
            return m_messages.size();
        }
    }

    //возвращает пустой список, если начальный индекс выходит за пределы списка сообщений
    public Iterable<LogEntry> range(int startFrom, int count) {
        synchronized (lockLog) {
            if (startFrom < 0 || startFrom >= m_messages.size()) {
                return Collections.emptyList();
            }
            int indexTo = Math.min(startFrom + count, m_messages.size());
            return m_messages.subList(startFrom, indexTo);
        }
    }

    //возвращает копию списка сообщений
    public Iterable<LogEntry> all() {
        synchronized (lockLog) {
            return new ArrayList<>(m_messages);
        }
    }
}
