package toy.subscribe.domain.bookmark.service;

import org.springframework.data.domain.Pageable;
import toy.subscribe.common.dtos.ResponseWrapper;

public interface BookmarkProvideService {
    ResponseWrapper provideBookmarkWrapper(Pageable pageable, String category, String title);
}
