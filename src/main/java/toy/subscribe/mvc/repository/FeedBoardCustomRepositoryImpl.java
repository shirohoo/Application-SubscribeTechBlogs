package toy.subscribe.mvc.repository;

import static toy.subscribe.domain.entity.QFeedBoard.feedBoard;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.domain.dto.FeedBoardDto;
import toy.subscribe.domain.entity.FeedBoard;

public class FeedBoardCustomRepositoryImpl extends QuerydslRepositorySupport implements FeedBoardCustomRepository {
    
    public FeedBoardCustomRepositoryImpl () {
        super(FeedBoard.class);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<FeedBoardDto> findPageByFeedBoard(Pageable pageable, String company, String title) {
        
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        QueryResults<FeedBoard> results = queryFactory
                .select(feedBoard)
                .from(feedBoard)
                .where(feedBoard.company.contains(company))
                .where(feedBoard.title.contains(title))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(feedBoard.id.desc())
                .fetchResults();

        List<FeedBoardDto> content = results.getResults()
                .stream()
                .map(FeedBoardDto::convertToFeedBoardDtoFrom)
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, results.getTotal());
    }
    
}