package org.example.Version_1.Supported;

import org.example.Version_1.Model.RegConfig;
import org.example.Version_1.Model.XmlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс для определения необходимых переменных для агента.
 */
public class ParserXml {
    /**
     * Считываем и парсим файл по заданному пути.
     */
    Optional<RegConfig> parse = XmlUtils.parse("src/main/resources/config.xml", RegConfig.class);

    /**
     * Определяем агента-инициатора (тот, кто запустит сообщения).
     * - Создаем строку (для получения имени инициатора) через StringBuilder (обычный String не работает...);
     * - Обращаемся к распаршеному файлу;
     * - Находим необходимую строку файла (по сути позицию словаря);
     * - Добавляем номер узла инициатора к ранее созданной строке;
     * - Создаем вспомогательную переменную, строкового типа и присваиваем ей полученное имя агента-инициатора;
     * - Возвращаем имя агента-инициатора;
     */
    public String findFirstAgent() {
        StringBuilder findAgent = new StringBuilder("Agent");
        parse.ifPresent(e -> findAgent.append(e.getFirst_agent()));
        String findAg = findAgent.toString();
        return findAg;
    }

    /**
     * Метод работает аналогично методу findFirstAgent
     */
    public String findLastAgent() {
        StringBuilder findAgent = new StringBuilder("Agent");
        parse.ifPresent(e -> findAgent.append(e.getLast_agent()));
        String findAg = findAgent.toString();
        return findAg;
    }

    /**
     * Метод по нахождению всех соседей агента (принимает позицию агента, чьих соседей необходимо найти).
     * - Создаем лист из строк;
     * - Используем распаршеный файл для нахождения необходимых параметров;
     * - Спускаемся на уровенье одного узла и вытягиваем список его соседей;
     */
    public List<String> findNeighborsAgents(int position) {
        List<String> findAgents = new ArrayList<>();
        parse.ifPresent(e -> findAgents.addAll(e.getNodeList().get(position).
                getNeighbourList().stream().map(ee -> "Agent" + ee.getPosition()).collect(Collectors.toList())));
        return findAgents;
    }

    /**
     * Метод работает аналогично методу findNeighborsAgents
     */
    public List<String> findWeightNeighborsAgents(int position) {
        List<String> findAgents = new ArrayList<>();
        parse.ifPresent(e -> findAgents.addAll(e.getNodeList().get(position).
                getNeighbourList().stream().map(ee -> ee.getWeight() + "").collect(Collectors.toList())));
        return findAgents;
    }
}
