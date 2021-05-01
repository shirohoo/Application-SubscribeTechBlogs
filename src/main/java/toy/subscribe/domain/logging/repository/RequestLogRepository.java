package toy.subscribe.domain.logging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import toy.subscribe.domain.board.FeedBoard;
import toy.subscribe.domain.logging.RequestLog;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long>, QuerydslPredicateExecutor<FeedBoard>, RequestLogCustomRepository {

}
