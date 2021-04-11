package toy.feed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import toy.feed.domain.FeedBoard;
import toy.feed.object.RSSFeedMessage;
import toy.feed.repository.FeedBoardRepository;

@Component
@RequiredArgsConstructor
public class FeedBoardFactory {
    
    private final FeedBoardRepository feedBoardRepository;
    
    public FeedBoard getFeedBoardFrom (RSSFeedMessage message) {
        if(isNoDuplicate(message)) {
            
            String url = message.getLink();
            
            if(url.contains("woowabros")) {
                message.setImgPath("/images/woowabros.png");
                message.setCompany("우아한형제들");
            }
            else if(url.contains("toss")) {
                message.setImgPath("/images/toss.png");
                message.setCompany("토스");
            }
            else if(url.contains("dailyhotel")) {
                message.setImgPath("/images/dailyhotel.png");
                message.setCompany("데일리호텔");
            }
            else if(url.contains("daangn")) {
                message.setImgPath("/images/daangn.png");
                message.setCompany("당근마켓");
            }
            else if(url.contains("kakao")) {
                message.setImgPath("/images/kakao.png");
                message.setCompany("카카오");
            }
            else if(url.contains("yanolja")) {
                message.setImgPath("/images/yanolja.png");
                message.setCompany("야놀자");
            }
            else if(url.contains("line")) {
                message.setImgPath("/images/line.png");
                message.setCompany("라인");
            }
            else if(url.contains("thefarmersfront")) {
                message.setImgPath("/images/kurly.png");
                message.setCompany("마켓컬리");
            }
            else if(url.contains("watcha")) {
                message.setImgPath("/images/watcha.png");
                message.setCompany("왓챠");
            }
            
            return FeedBoard.builder()
                            .title(message.getTitle())
                            .company(message.getCompany())
                            .imgPath(message.getImgPath())
                            .guid(message.getGuid())
                            .build();
        }
        return null;
    }
    
    private boolean isNoDuplicate (RSSFeedMessage message) {
        return feedBoardRepository.countByGuid(message.getGuid().trim()) == 0L;
    }
    
}
