package net.smourad.signcts.utils;

import java.util.ArrayList;
import java.util.List;

public enum DefaultFontSignInfo {

    A('A', 6),
    a('a', 6),
    B('B', 6),
    b('b', 6),
    C('C', 6),
    c('c', 6),
    D('D', 6),
    d('d', 6),
    E('E', 6),
    e('e', 6),
    F('F', 6),
    f('f', 5),
    G('G', 6),
    g('g', 6),
    H('H', 6),
    h('h', 6),
    I('I', 4),
    i('i', 2),
    J('J', 6),
    j('j', 6),
    K('K', 6),
    k('k', 5),
    L('L', 6),
    l('l', 3),
    M('M', 6),
    m('m', 6),
    N('N', 6),
    n('n', 6),
    O('O', 6),
    o('o', 6),
    P('P', 6),
    p('p', 6),
    Q('Q', 6),
    q('q', 6),
    R('R', 6),
    r('r', 6),
    S('S', 6),
    s('s', 6),
    T('T', 6),
    t('t', 4),
    U('U', 6),
    u('u', 6),
    V('V', 6),
    v('v', 6),
    W('W', 6),
    w('w', 6),
    X('X', 6),
    x('x', 6),
    Y('Y', 6),
    y('y', 6),
    Z('Z', 6),
    z('z', 6),
    NUM_1('1', 6),
    NUM_2('2', 6),
    NUM_3('3', 6),
    NUM_4('4', 6),
    NUM_5('5', 6),
    NUM_6('6', 6),
    NUM_7('7', 6),
    NUM_8('8', 6),
    NUM_9('9', 6),
    NUM_0('0', 6),
    EXCLAMATION_POINT('!', 2),
    AT_SYMBOL('@', 8),
    NUM_SIGN('#', 6),
    DOLLAR_SIGN('$', 6),
    PERCENT('%', 6),
    UP_ARROW('^', 6),
    AMPERSAND('&', 6),
    ASTERISK('*', 4),
    LEFT_PARENTHESIS('(', 4),
    RIGHT_PARENTHESIS(')', 4),
    MINUS('-', 6),
    UNDERSCORE('_', 6),
    PLUS_SIGN('+', 6),
    EQUALS_SIGN('=', 6),
    LEFT_CURL_BRACE('{', 4),
    RIGHT_CURL_BRACE('}', 4),
    LEFT_BRACKET('[', 4),
    RIGHT_BRACKET(']', 4),
    COLON(':', 2),
    SEMI_COLON(';', 2),
    DOUBLE_QUOTE('"', 4),
    SINGLE_QUOTE('\'', 2),
    LEFT_ARROW('<', 5),
    RIGHT_ARROW('>', 5),
    QUESTION_MARK('?', 6),
    SLASH('/', 6),
    BACK_SLASH('\\', 6),
    LINE('|', 2),
    TILDE('~', 8),
    TICK('`', 3),
    PERIOD('.', 2),
    COMMA(',', 2),
    SPACE(' ', 4),
    DEFAULT(' ', 4);

    private final char character;
    private final int length;

    DefaultFontSignInfo(final char character, final int length) {
        this.character = character;
        this.length = length;
    }

    public char getCharacter() {
        return this.character;
    }

    public int getLength() {
        return this.length;
    }

    public int getBoldLength() {
        return this == DefaultFontSignInfo.SPACE ? this.getLength() : this.getLength() + 1;
    }

    public static DefaultFontSignInfo getDefaultFontSignInfo(char c) {
        for (DefaultFontSignInfo dFI : DefaultFontSignInfo.values()) {
            if (dFI.getCharacter() == c) return dFI;
        }

        return DefaultFontSignInfo.DEFAULT;
    }

    public static int getStringSignLength(String str) {
        int count = 0;

        for (char c : str.toCharArray()) {
            count += getDefaultFontSignInfo(c).getLength();
        }

        return count;
    }

    public static boolean isNotSignLineOverflow(String str) {
        return getStringSignLength(str) > 90;
    }

    public static List<String> cutInSignLine(String str) {
        StringBuilder builder = new StringBuilder();
        List<String> result = new ArrayList<>();

        int count = 0;
        for (char c : str.toCharArray()) {
            if ((count += getDefaultFontSignInfo(c).getLength()) > 90) {
                result.add(builder.toString());
                builder = new StringBuilder();
            }

            builder.append(c);
        }

        result.add(builder.toString());
        return result;
    }
}
