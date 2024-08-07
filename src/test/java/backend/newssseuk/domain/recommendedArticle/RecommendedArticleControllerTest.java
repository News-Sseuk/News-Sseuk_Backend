package backend.newssseuk.domain.recommendedArticle;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.domain.user.repository.UserRepository;
import backend.newssseuk.domain.userHistory.UserHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
//@ExtendWith(MockitoExtension.class)
class RecommendedArticleControllerTest {
//    @MockBean
//    private RecommendedArticleService recommendedArticleService;
//    @MockBean
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private RecommendedArticleController recommendedArticleController;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void setUp(){
//        mockMvc = MockMvcBuilders.standaloneSetup(recommendedArticleController).build();
//    }
//
//    @BeforeEach
//    public void articleSaved(){
//        Article sampleArticle = article("66a68b65dbef9077e3b126cd");
//
//    }
//    @Test
//    void personalRecommendedArticles() throws Exception {
//        //given
//        Article sampleArticle = article("66a68b65dbef9077e3b126cd");
//
//
//        Mockito.doReturn(sampleArticle).when(recommendedArticleService).personalRecommendedArticles(ArgumentMatchers.any(articleId));
//        //when
//
//        //then
//    }
//
//    private User user(){
//        return User.builder()
//                .email("test@test.com")
//                .name("admin")
//                .build();
//    }
//    private Article article(String nosqlId){
//        return Article.builder()
//                .nosqlId(nosqlId)
//                .build();
//    }
}