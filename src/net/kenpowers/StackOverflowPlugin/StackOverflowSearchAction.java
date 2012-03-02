package net.kenpowers.StackOverflowPlugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * Action class for searching StackOverflow from the tools menu.
 *
 * @author Kenneth Powers
 */
public class StackOverflowSearchAction extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
    }

    /**
     * Gets the word from the editor where the caret is.
     *
     * @param editorText  The text from the editor.
     * @param caretOffset Where teh caret is in the editor.
     * @return The word where the caret is in the editor.
     */
    private String getWordAtCaret(CharSequence editorText, int caretOffset) {
        if (editorText.length() == 0) return null;

        if (caretOffset > 0 && !Character.isJavaIdentifierPart(editorText.charAt(caretOffset))
            && Character.isJavaIdentifierPart(editorText.charAt(caretOffset - 1))) {
            caretOffset--;
        }

        if (Character.isJavaIdentifierPart(editorText.charAt(caretOffset))) {
            int start = caretOffset;
            int end = caretOffset;

            while (start > 0 && Character.isJavaIdentifierPart(editorText.charAt(start - 1))) {
                start--;
            }

            while (end < editorText.length() && Character.isJavaIdentifierPart(editorText.charAt(end))) {
                end++;
            }

            return editorText.subSequence(start, end).toString();
        }

        return null;
    }
}
