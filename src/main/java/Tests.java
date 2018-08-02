import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxg on 2018/7/31.
 */
public class Tests {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(4);
        list.add(5);
        list.stream().forEach(System.out::println);
    }
}
