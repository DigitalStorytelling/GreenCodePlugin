package com.hfu.greencoding;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;

public class PopupDialogAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // Get References to dokument
        Project project = e.getProject();
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();

        // Save selected text
        String selectedText = caretModel.getCurrentCaret().getSelectedText();
        Caret primaryCaret = caretModel.getPrimaryCaret();
        VisualPosition selectionStartPosition = primaryCaret.getSelectionStartPosition();
        int startLine = selectionStartPosition.getLine();

        // Prepend line numbers to each line of selected text
        StringBuilder modifiedText = new StringBuilder();
        String[] lines = selectedText.split("\n");
        for (int i = 0; i < lines.length; i++) {
            modifiedText.append(startLine + i + 1).append(": ").append(lines[i]);
            if (i < lines.length - 1) {
                modifiedText.append("\n");
            }
        }

        // Change Text in Editor
        String newText = "This code is sooooo grrrreeeeeennnn";
        replaceSelectedTextWithNewText(project, editor, primaryCaret, newText);

        // Den zuvor modifizierten Text in einer Variablen behalten
        String savedText = modifiedText.toString();
        System.out.println(savedText);
    }

    private void replaceSelectedTextWithNewText(Project project, Editor editor, Caret primaryCaret, String newText) {
        WriteCommandAction.runWriteCommandAction(project, () -> {
            int selectionStart = primaryCaret.getSelectionStart();
            int selectionEnd = primaryCaret.getSelectionEnd();
            editor.getDocument().replaceString(selectionStart, selectionEnd, newText);
        });
    }
}