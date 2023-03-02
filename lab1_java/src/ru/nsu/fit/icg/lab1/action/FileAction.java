package ru.nsu.fit.icg.lab1.action;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FileAction extends MyAction {

    private static final String iconsDirName = "file/";

    public FileAction(String name, String iconFileName) {
        super(name,iconsDirName,iconFileName,name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
