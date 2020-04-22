package br.com.south.batch.sales.utils.message;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;


@Component
public class MessageUtils {

    private final MessageSource messageSource;

    private static MessageSource messageSourceStatic;


    public MessageUtils(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @PostConstruct
    public void init() {
        messageSourceStatic = messageSource;
    }

    public static String getMessage(MessageCodeEnum code, Object... param){
        return messageSourceStatic.getMessage(code.getMessageKey(), param, Locale.ROOT);
    }

    public static String getMessage(MessageCodeEnum code){
        return messageSourceStatic.getMessage(code.getMessageKey(), null, Locale.ROOT);
    }
}
