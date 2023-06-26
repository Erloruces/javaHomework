import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;



public class Main {
    public static void main(String[] args) throws IOException {
        // создание тексового файла
        File file = new File("result.txt");
        try (FileWriter writer = new FileWriter(file, true)) {
//            writer.write(" \n");

        // поиск номера игры
        int numberGame = 1;
        FileReader reader = new FileReader(file);

            // чтение текстового файла
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuffer textResult = new StringBuffer();
            String cot;

            // проверка на пустоту файла
            if (file.length() != 0) {
                while ((cot = bufferedReader.readLine()) != null) {
                    textResult.append(cot);
                }
                // поиск последнего номера игры
                Pattern pattern = Pattern.compile("№\\d*");
                Matcher matcher = pattern.matcher(textResult);
                StringBuffer buffer = new StringBuffer();
                while (matcher.find()) {
                    buffer.replace(0, buffer.length(), textResult.substring(matcher.start(), matcher.end()));
                    numberGame = Integer.parseInt(buffer.deleteCharAt(0).toString());
                    numberGame++;
                }
            }






        // запсиь даты и номера игры
        Date dateNow = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        String date = sdf.format(dateNow);

        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        // список [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        // генерация четырехзначного числа
        StringBuilder sb = new StringBuilder();
        int y = 0;
        for (int i = 0; i < 4; i++) {
            int rnd = random.nextInt(list.size());
            sb.append(list.get(rnd));
            y = y * 10 + list.get(rnd);
            list.remove(rnd);
        }
        String secret = String.valueOf(sb);
        String head = "Game №" + numberGame + " " + date + " " + "Загаданная строка " + secret;
        writer.write(head + "\n");
        System.out.println(head);

        // цикл проверки числа
        int count = 0;
        while (true) {
            // проверка вводимого числа
            String answer;
            while (true) {
                System.out.println("    Введите четырехзначное число:");
                String i = scanner.next();
                if (i.length() == 4 && reg(i)) {
                    answer = i;
                    break;
                }
                System.out.println("    Неверное число");
            }

            // проверка на совпадения
            String[] secretToArray = secret.split("");
            String[] answerToArray = answer.split("");

            int cow = 0;
            int bull = 0;
            for (int i = 0; i < secretToArray.length; i++) {
                if (secretToArray[i].equals(answerToArray[i])) {
                    bull++;
                } else {
                    for (String s : answerToArray) {
                        if (secretToArray[i].equals(s)) {
                            cow++;
                        }
                    }
                }

            }
            // составление строки запроса
            String strCow;
            if (cow == 0) {
                strCow = " коров ";
            } else if (cow == 1) {
                strCow = " корова ";
            } else {
                strCow = " коровы ";
            }

            String strBull;
            if (bull == 0) {
                strBull = " быков";
            } else if (bull == 1) {
                strBull = " бык";
            } else {
                strBull = " быка";
            }
            // вывод результата
            String body = "    Запрос: " + answer + " Ответ: " + cow + strCow + bull + strBull;
            writer.write(body + "\n");
            System.out.println(body);
            count++;
            if (secret.equals(answer)) {
                break;
            }
        }
        // вывод итога игры
        String attempt;
        if (count%10 == 1) {
            attempt = " попытку.";
        } else if ((Arrays.asList(2, 3, 4).contains(count%10))) {
            attempt = " попытки.";
        } else {
            attempt = " попытки.";
        }
        String result = "    Строка была угадана за " + count + attempt;
        writer.write(result + "\n");
        System.out.println(result);

        }
    }


    // регулярка для проверки ввода на цифры
    static boolean reg(String s) {

        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(s);

        return m.matches();
    }
}