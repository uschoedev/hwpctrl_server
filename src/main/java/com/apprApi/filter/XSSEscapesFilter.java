package com.apprApi.filter;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * XSS ( Cross Site Scripting ) 방어 설정
 * @Class XSSEscapesFilter
 * @Author uschoe
 * @Date 2021.12.21
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class XSSEscapesFilter extends CharacterEscapes {

    private static final Logger logger = LoggerFactory.getLogger(XSSEscapesFilter.class);

    private int[] asciiEscapes;

    public XSSEscapesFilter() {

        logger.info(this.getClass().getSimpleName() + ".Constructor");

        // XSS 방어를 위한 특수문자 지정
        asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();

        // XSS 케이스에 해당하는 문자를 치환하기 위한 설정
        asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['"'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['('] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes[')'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['#'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;

    }

    @Override
    public int[] getEscapeCodesForAscii() {
        logger.info(this.getClass().getSimpleName() + ".getEscapeCodesForAscii");
        return asciiEscapes;
    }

    @Override
    public SerializableString getEscapeSequence(int ch) {

        logger.info(this.getClass().getSimpleName() + ".getEscapeSequence");

        SerializedString serializedString;

        char charAt = (char) ch;

        /** 문자열이 서로게이트 여부인지 확인한다. */
        if(Character.isHighSurrogate(charAt) || Character.isLowSurrogate(charAt)) {

            StringBuilder sb = new StringBuilder();

            sb.append("\\u");
            sb.append(String.format("%04x", charAt));

            serializedString = new SerializedString(sb.toString());

        } else{
            serializedString = new SerializedString(StringEscapeUtils.escapeHtml4(Character.toString(charAt)));
        }

        return serializedString;

    }

}
