import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");

        Collection<Person> person = generateData(names, families);

        int countMinors = (int) getCountMinors(person);
        List<String> recruits = getRecruits(person);
        List<Person> workingPopulations = getWorkingPopulations(person);

//        Найти количество несовершеннолетних (т.е. людей младше 18 лет).

        System.out.println("Количество людей младше 18 лет " + countMinors + " чел.");

//        Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет).

        for (String recruit : recruits) {
            System.out.println(recruit);
        }

//        Получить отсортированный по фамилии список потенциально работоспособных
//        людей с высшим образованием в выборке (т.е. людей с высшим образованием
//        от 18 до 60 лет для женщин и до 65 лет для мужчин).

        for (Person workingPopulation : workingPopulations) {
            System.out.println(workingPopulation);
        }

    }

    public static long getCountMinors(Collection<Person> persons) {
        return persons.stream()
                .filter(p -> p.getAge() < 18)
                .count();
    }

    public static List<String> getRecruits(Collection<Person> persons) {
        return persons.stream()
                .filter(p -> p.getAge() >= 18 && p.getAge() < 27)
                .filter(p -> p.getSex().equals(Sex.MAN))
                .map(Person::getFamily)
                .limit(10)
                .collect(Collectors.toList());
    }

    public static List<Person> getWorkingPopulations(Collection<Person> persons) {
        return persons.stream()
                .filter(p -> p.getEducation() == (Education.HIGHER))
                .filter(p -> (p.getAge() >= 18 && (p.getAge()) < (p.getSex() == Sex.MAN ? 65 : 60)))
                .sorted(Comparator.comparing(Person::getFamily))
                .limit(10)
                .collect(Collectors.toList());
    }

    public static Collection<Person> generateData(List<String> names, List<String> families) {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)]));
        }
        return persons;
    }
}
