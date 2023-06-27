package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import model.log.LogChangeListener;
import model.log.LogEntry;
import model.log.LogWindowSource;

import static localization.ApplicationLocalizer.applicationLocalizer;

public class LogWindow extends JInternalFrame implements LogChangeListener, Observer {
    private LogWindowSource m_logSource;
    private TextArea m_logContent;
    private static String titleWindow = applicationLocalizer.getLocalizedText("titleLogWindow");

    public LogWindow(LogWindowSource logSource) {
        super(titleWindow, true, true, true, true);
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);

        applicationLocalizer.addObserver(this);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            String argument = (String) arg;
            if (argument.equals("CHANGE_LANGUAGE")) {
                updateComponents();
            }
        }
    }

    private void updateComponents() {
        titleWindow = applicationLocalizer.getLocalizedText("titleLogWindow");
        this.setTitle(titleWindow);
    }
}
