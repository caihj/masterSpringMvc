package masterSpringMvc.search;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {


    public List<Tweet> search(String searchType, List<String> keywords) {

        List<Tweet> list = new ArrayList<>();
        Tweet tweet = new Tweet();
        tweet.setPhoto("http://www.baidu.com/img/bd_logo1.png");
        tweet.setTitle("百度");
        tweet.setText("这是说明");

        list.add(tweet);
        return  list;
    }
}
