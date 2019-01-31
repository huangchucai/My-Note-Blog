package hcc.company.test;

import java.util.HashMap;


/*
*
描述
给一个字符串 (只包含26个字母)，统计每个字母出现的次数 (返回类型为HashMap)

样例
给出 numbers = "hcc", 返回一个HashMap counts, 里面包含所有26个字母出现的次数

* */
public class WordCounter {
    public HashMap count(String input) {
        // 写具体实现
        HashMap<Character, Integer> worldcount = new HashMap<Character, Integer>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (worldcount.containsKey(c)) {
                int count = worldcount.get(c);
                count += 1;
                worldcount.put(c, count);
            } else {
                worldcount.put(c, 1);
            }
        }
        return worldcount;
    }

    public static void main(String[] args) {
        WordCounter wordCounter = new WordCounter();
        System.out.println(wordCounter.count("hcc"));
        System.out.println(wordCounter.count("hccyxLove"));
        System.out.println(wordCounter.count("hccyxLovelll"));
        System.out.println(wordCounter.count("csyxLovelll"));
        System.out.println(wordCounter.count("vip"));
    }
}
