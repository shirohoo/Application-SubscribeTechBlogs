package toy.subscribe.domain.board.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import toy.subscribe.domain.board.FeedBoard;
import toy.subscribe.domain.board.dto.Company;
import toy.subscribe.domain.board.dto.RSSFeedMessage;
import toy.subscribe.domain.board.parser.JsonReader;
import toy.subscribe.domain.board.repository.FeedBoardRepository;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeedBoardFactory {
    
    private final FeedBoardRepository feedBoardRepository;
    
    public FeedBoard findFeedBoardFrom(RSSFeedMessage message) {
        if(isNoDuplicate(message)) {
            String url = message.getLink();
            List<Company> companies = JsonReader.readCompanies();

            if (companies != null) {
                for (int i = 0; i < companies.size(); i++) {
                    if (url.contains(companies.get(i).getKey())) {
                        message.setCompany(companies.get(i).getName());
                        message.setImgPath(companies.get(i).getImgPath());
                        break;
                    }
                }

                return FeedBoard.builder()
                        .title(message.getTitle())
                        .company(message.getCompany())
                        .imgPath(message.getImgPath())
                        .guid(message.getGuid())
                        .build();
            }
        }
        return null;
    }
    
    private boolean isNoDuplicate(RSSFeedMessage message) {
        return feedBoardRepository.countByGuid(message.getGuid().trim()) == 0L;
    }
    
}
