package be.unamur.fpgen.utils;

import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.interlocutor.InterlocutorTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for correspondence between InterlocutorTypeEnum and MessageTypeEnum which are related concepts
 */
public class TypeCorrespondenceMapper {

        private static final Map<InterlocutorTypeEnum, MessageTypeEnum> enumMap = new HashMap<>();
        private static final Map<MessageTypeEnum, InterlocutorTypeEnum> enumMap2 = new HashMap<>();

        static {
            enumMap.put(InterlocutorTypeEnum.GENUINE, MessageTypeEnum.GENUINE);
            enumMap.put(InterlocutorTypeEnum.HARASSER, MessageTypeEnum.HARASSMENT);
            enumMap.put(InterlocutorTypeEnum.SOCIAL_ENGINEER, MessageTypeEnum.SOCIAL_ENGINEERING);

            enumMap2.put(MessageTypeEnum.GENUINE, InterlocutorTypeEnum.GENUINE);
            enumMap2.put(MessageTypeEnum.HARASSMENT,InterlocutorTypeEnum.HARASSER);
            enumMap2.put(MessageTypeEnum.SOCIAL_ENGINEERING, InterlocutorTypeEnum.SOCIAL_ENGINEER);
        }

        public static MessageTypeEnum getCorrespondence(InterlocutorTypeEnum interlocutorTypeEnum) {
            return enumMap.get(interlocutorTypeEnum);
        }

        public static InterlocutorTypeEnum getCorrespondence(MessageTypeEnum messageType) {
            return enumMap2.get(messageType);
        }
}
