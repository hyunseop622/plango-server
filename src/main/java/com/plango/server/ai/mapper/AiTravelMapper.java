package com.plango.server.ai.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plango.server.ai.dto.AiTravelRequest;
import com.plango.server.travel.dto.TravelCreateRequest;
import com.plango.server.user.UserService;
import org.springframework.stereotype.Component;

@Component
public class AiTravelMapper {

    private final ObjectMapper mapper;

    public AiTravelMapper(ObjectMapper mapper, UserService userService) {
        this.mapper = mapper;
    }

    // 앱 -> 서버 요청 ai로 변환
    public AiTravelRequest translateAi(TravelCreateRequest req, String userMbti) {
        String publicId = req.userPublicId();
        AiTravelRequest aiTravelRequest = new AiTravelRequest(
                req.travelType().name(),
                req.travelDest(),
                req.startDate().toString(),
                req.endDate().toString(),
                req.themes().get(0),
                req.themes().get(1),
                req.themes().get(2),
                userMbti,
                req.companionType().name()
        );

        return aiTravelRequest;
    }

    public String buildUserJson(AiTravelRequest req) {
        try {
            String pretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(req);
            System.out.println(pretty);
            return mapper.writeValueAsString(req);
        }catch (Exception e){ throw new RuntimeException(e);}
    }

    public String systemPrompt(){
        return"""
        당신은 여행 플래너입니다. 입력 JSON을 바탕으로 '일자별 코스'만 생성하세요.

        [하드 룰]
        - 출력은 오직 JSON만. 마크다운/설명/추가 텍스트 금지.
        - OUTPUT SCHEMA의 구조·필드명·자료형을 정확히 따름. **정해진 필드 외 추가 금지(additional properties not allowed)**.
        - 입력 startDate~endDate의 ‘포함 범위’ 일수 == 출력 days.length (반드시 동일).
        - 각 day의 courses는 상황에 맞게 생성(보통 3~6개 권장).
        - 각 course는 모두 필수:
          - order: 1부터 시작, 1씩 증가(1..N), 중복/누락 금지.
          - locationName: 빈 문자열 금지.
          - lat/lng: **확실할 때만 수치**, 불확실하면 **null(추측 금지)**.
          - note: 짧은 설명, 모르면 "".
          - theme: theme1~3 중 가장 적합, 없으면 "".
          - howLong: 분 단위 정수(보통 60~180 권장).
        - 대문자 코드 등 입력의 표기 규칙을 그대로 따름.
        - 날짜 문자열이나 dayIndex는 출력하지 않음(서버가 처리).

        [입력 형식]
        {
          "travelType": <String>,       // "DOMESTIC" 또는 "INTERNATIONAL"
          "travelDest": <String>,
          "startDate": <String>,        // ISO-8601: "YYYY-MM-DD"
          "endDate": <String>,          // ISO-8601: "YYYY-MM-DD"
          "theme1": <String>,           
          "theme2": <String>,           
          "theme3": <String>,           
          "userMbti": <String>,         // 16타입 중 하나: ISTJ, ISFJ, INFJ, INTJ, ISTP, ISFP, INFP, INTP, ESTP, ESFP, ENFP, ENTP, ESTJ, ESFJ, ENFJ, ENTJ
          "companionType": <String>     // 예: "SOLO","COUPLE","FAMILY","FRIENDS"
        }
        - 모든 입력 필드는 null이 아닙니다.
        - travelType은 반드시 "DOMESTIC" 또는 "INTERNATIONAL"입니다.

        [OUTPUT SCHEMA]  // 형태만 제시(값 예시 없음)
        {
          "days": [
            {
              "courses": [
                {
                  "order": <integer>,
                  "locationName": <string>,
                  "lat": <number|null>,
                  "lng": <number|null>,
                  "note": <string>,
                  "theme": <string>,
                  "howLong": <integer>
                }
              ]
            }
          ]
        }
        """;
    }
}

