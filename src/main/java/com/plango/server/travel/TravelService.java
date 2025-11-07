package com.plango.server.travel;

import com.plango.server.ai.AiService;
import com.plango.server.travel.dto.TravelCreateRequest;
import com.plango.server.travel.dto.TravelDetailResponse;
import com.plango.server.travel.dto.TravelSummaryResponse;
import com.plango.server.travel.entity.TravelEntity;
import com.plango.server.travel.repos.TravelRepository;
import com.plango.server.user.UserEntity;
import com.plango.server.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.time.LocalDate;
import java.util.List;

@Service
public class TravelService {

    private final TravelRepository travelRepository;
    private final UserService userService;
    private final AiService aiService;

    public TravelService(TravelRepository travelRepository, AiService aiService, UserService userService) {
        this.travelRepository = travelRepository;
        this.aiService = aiService;
        this.userService = userService;
    }

    // AI 선 처리 후 DB  저장
    // Create
    @Transactional
    public TravelDetailResponse createTravel(TravelCreateRequest req) {
        //AI 응답 (여행 요청 + MBTI)
        String publicId = req.userPublicId();
        String userMbti = userService.getUserMbtiByPublicId(publicId);
        TravelDetailResponse details = aiService.generateTravelDetail(req);

        // DB 저장
        UserEntity ue = userService.getUserEntityByPublicId(publicId);
        String travelDest = req.travelDest();
        LocalDate start = LocalDate.parse(req.startDate());
        LocalDate end = LocalDate.parse(req.endDate());
        List<String> themes = req.themes();
        String theme1 = themes.get(0);
        String theme2 = themes.get(1);
        String theme3 = themes.get(2);


        TravelEntity travelEntity = new TravelEntity(
                ue, travelDest, start,end,
                req.travelType(), req.companionType(), theme1,
                theme2, theme3
        );

        travelRepository.saveAndFlush(travelEntity);

        return details;
    }

    //Read 모든 여행
    public List<TravelSummaryResponse> readAllTravel(String publicId) {
        //유저의 내부 키 빠르게 찾기
        UserEntity ue = userService.getUserEntityByPublicId(publicId);
        List<TravelEntity> travels = travelRepository.findByUser(ue);

        return travels.stream()
                .map(t -> new TravelSummaryResponse(
                        t.getTravelId(),
                        t.getTravelType(),
                        t.getTravelDest(),
                        t.getTravelStart().toString(),
                        t.getTravelEnd().toString(),
                        List.of(t.getTravelTheme1(), t.getTravelTheme2(), t.getTravelTheme3()),
                        t.getCompanionType()
                ))
                .toList();
    }


}
