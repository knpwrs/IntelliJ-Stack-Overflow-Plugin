package net.kenpowers.StackOverflowPlugin;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Action class for searching StackOverflow from the tools menu.
 *
 * @author Kenneth Powers
 */
public class StackOverflowSearchAction extends AnAction {
    /**
     * Opens the default system browser with a Stack Overflow search to the word where the caret is located.
     *
     * @param ae The action from IntelliJ.
     */
    public void actionPerformed(AnActionEvent ae) {
        Editor editor = ae.getData(DataKeys.EDITOR);
        String word = getWordAtCaret(editor.getDocument().getCharsSequence(), editor.getCaretModel().getOffset());
        if (word != null) {
            String query;
            try {
                query = URLEncoder.encode(word, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return;
            }
            BrowserUtil.launchBrowser("http://stackoverflow.com/search?q=" + query);
        }
    }

    /**
     * Enables / disables the action depending on the current context.
     *
     * @param ae The action from IntelliJ.
     */
    @Override
    public void update(AnActionEvent ae) {
        ae.getPresentation().setEnabled(ae.getData(DataKeys.EDITOR) != null);
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
