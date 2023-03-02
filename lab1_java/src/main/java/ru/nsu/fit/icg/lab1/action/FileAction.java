package ru.nsu.fit.icg.lab1.action;

import java.awt.event.ActionEvent;

public class FileAction extends CommandAction {

    public FileAction(String name, String iconFileName) {
        super(name,"file/" + iconFileName,name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
