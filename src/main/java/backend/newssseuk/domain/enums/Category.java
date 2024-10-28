package backend.newssseuk.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    PRESIDENTIAL_OFFICE("대통령실"),
    NATIONAL_ASSEMBLY_POLITICAL_PARTIES("국회_정당"),
    NORTH_KOREA("북한"),
    ADMINISTRATION("행정"),
    NATIONAL_DEFENSE_DIPLOMACY("국방_외교"),
    GENERAL_POLITICS("정치일반"),
    FINANCE("금융"),
    STOCK_MARKET("증권"),
    INDUSTRY_BUSINESS("산업_재계"),
    SMEs_STARTUPS("중기_벤처"),
    REAL_ESTATE("부동산"),
    GLOBAL_ECONOMY("글로벌경제"),
    CONSUMER_ECONOMY("생활경제"),
    GENERAL_ECONOMY("경제일반"),
    INCIDENTS_ACCIDENTS("사건사고"),
    EDUCATION("교육"),
    LABOR("노동"),
    MEDIA("언론"),
    ENVIRONMENT("환경"),
    HUMAN_RIGHTS_WELFARE("인권_복지"),
    FOOD_HEALTHCARE("식품_의료"),
    LOCAL("지역"),
    PEOPLE("인물"),
    GENERAL_SOCIAL("사회일반"),
    HEALTH_INFO("건강정보"),
    AUTOMOBILES_TEST_DRIVES("자동차_시승기"),
    ROADS_TRAFFIC("도로_교통"),
    TRAVEL_LEISURE("여행_레저"),
    FOOD_RESTAURANTS("음식_맛집"),
    FASHION_BEAUTY("패션_뷰티"),
    PERFORMANCES_EXHIBITIONS("공연_전시"),
    BOOKS("책"),
    RELIGION("종교"),
    WEATHER("날씨"),
    GENERAL_LIFESTYLE_CULTURE("생활문화일반"),
    MOBILE("모바일"),
    INTERNET_SOCIAL_MEDIA("인터넷_SNS"),
    COMMUNICATIONS_NEW_MEDIA("통신_뉴미디어"),
    GENERAL_IT("IT일반"),
    SECURITY_HACKING("보안_해킹"),
    COMPUTERS("컴퓨터"),
    GAMES_REVIEWS("게임_리뷰"),
    GENERAL_SCIENCE("과학 일반"),
    ASIA_AUSTRALIA("아시아_호주"),
    AMERICAS("미국_중남미"),
    EUROPE("유럽"),
    MIDDLE_EAST_AFRICA("중동_아프리카"),
    GENERAL_WORLD("세계일반");

    private final String korean;
}
