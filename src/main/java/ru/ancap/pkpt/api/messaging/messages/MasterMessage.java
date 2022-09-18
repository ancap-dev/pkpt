package ru.ancap.pkpt.api.messaging.messages;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.ancap.pkpt.api.PKPT;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class MasterMessage {
    
    private final String secretWord;
    private final String session;
    private final String mainMessage;

    public static MasterMessage read(String netForm) throws UnreadableNetformException {
        String[] args = netForm.split(PKPT.MAIN_SEPARATOR);
        if (args.length != 4) {
            throw new UnreadableNetformException("Сообщение не по протоколу - аргументов "+args.length+", а должно быть 4");
        }
        if (!args[0].equals(PKPT.DOMAIN)) {
            throw new UnreadableNetformException("Сообщение не по протоколу - домен "+args[0]+", а не "+PKPT.DOMAIN);
        }
        return new MasterMessage(args[1], args[2], args[3]);
    }

    public String produceNetForm() {
        return PKPT.DOMAIN + PKPT.MAIN_SEPARATOR + secretWord + PKPT.MAIN_SEPARATOR + session + PKPT.MAIN_SEPARATOR + mainMessage;
    }
}
