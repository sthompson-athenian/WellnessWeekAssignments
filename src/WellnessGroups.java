import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WellnessGroups {
    // A function that updates the included Workshops and Students so that every
    // Student is assigned to 3 sessions of Workshops, and the following properties
    // are met:
    // --Each student takes three different Categories of Workshops
    // --No workshop has more than 17 students
    // --Each student who included preferences should be placed in at least one of their
    //       preferred Workshops
    public static void formGroups(List<Student> students, List<Workshop> workshops) throws IllegalStateException {
        // Your task: implement this function.
    }

    public static void main(String[] args) {
        // --------------------------
        // Test 1: Small dataset
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 1: Small dataset");

        List<Workshop> workshops = new ArrayList<>();
        workshops.add(new Workshop("Running", Category.PHYSICAL_HEALTH));
        workshops.add(new Workshop("Spikeball", Category.PHYSICAL_HEALTH));
        workshops.add(new Workshop("Digital Well-being", Category.MENTAL_HEALTH));
        workshops.add(new Workshop("The Science of Sleep", Category.MENTAL_HEALTH));
        workshops.add(new Workshop("Laundry 101", Category.LIFE_SKILL));
        workshops.add(new Workshop("Intro to Microwave Cooking", Category.LIFE_SKILL));
        workshops.add(new Workshop("How to Make Friends", Category.RELATIONSHIPS));
        workshops.add(new Workshop("How to Keep Friends", Category.RELATIONSHIPS));
        List<Student> students = new ArrayList<>();
        students.add(new Student("Aaron", workshops.get(0), workshops.get(1), workshops.get(2), workshops.get(3), workshops.get(4)));
        students.add(new Student("Baron", workshops.get(0), workshops.get(1), workshops.get(2), workshops.get(3), workshops.get(4)));
        students.add(new Student("Charon", workshops.get(0), workshops.get(1), workshops.get(2), workshops.get(3), workshops.get(4)));
        students.add(new Student("Darin", workshops.get(7), workshops.get(1), workshops.get(2), workshops.get(3), workshops.get(4)));
        students.add(new Student("Erin", workshops.get(6), workshops.get(1), workshops.get(2), workshops.get(3), workshops.get(4)));
        students.add(new Student("Faron", workshops.get(6), workshops.get(1), workshops.get(2), workshops.get(3), workshops.get(4)));
        students.add(new Student("Garron", workshops.get(5), workshops.get(1), workshops.get(2), workshops.get(3), workshops.get(4)));
        students.add(new Student("Heron", workshops.get(5), workshops.get(1), workshops.get(2), workshops.get(3), workshops.get(4)));
        formGroups(students, workshops);
        printWorkshops(workshops);
        System.out.println(isValid(students, workshops));

        // --------------------------
        // Test 2: Real dataset
        // --------------------------
//        List<Workshop> realWorkshops = new ArrayList<>();
//        Map<String, Workshop> realWorkshopsMap = new HashMap<>();
//        try (BufferedReader br = new BufferedReader(new FileReader("data/workshop_categories.csv"))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(",");
//                realWorkshops.add(new Workshop(values[1],values[0]));
//                realWorkshopsMap.put(values[1], realWorkshops.get(realWorkshops.size() - 1));
//            }
//        } catch (FileNotFoundException e) {
//            System.err.println("Unable to find input csv");
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            System.err.println("Unable to read input csv");
//            throw new RuntimeException(e);
//        }
//
//        List<Student> realStudents = new ArrayList<>();
//        try (BufferedReader br = new BufferedReader(new FileReader("data/student_choices.csv"))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(",");
//                realStudents.add(new Student(values[0], Arrays.stream(values).skip(1).filter((e) -> !e.isEmpty()).map(realWorkshopsMap::get).collect(Collectors.toSet())));
//            }
//        } catch (FileNotFoundException e) {
//            System.err.println("Unable to find input csv");
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            System.err.println("Unable to read input csv");
//            throw new RuntimeException(e);
//        }
//        formGroups(realStudents, realWorkshops);
//
//        printWorkshops(realWorkshops);
//
//        printStudents(realStudents);
//
//        System.out.println(isValid(realStudents, realWorkshops));
    }

    // A helper function that will print out all students, with their schedules
    private static void printStudents(Collection<Student> students) {
        for (Student s : students) {
            System.out.print(s.name + ": ");
            int i=1;
            for (Workshop w : s.sessions) {
                System.out.print(i + ": ");
                i++;
                if (w == null) {
                    System.out.print("NULL ");
                } else {
                    System.out.print(w.name + " ");
                }
            }
            System.out.println();
        }
    }

    // A helper function that will print out all workshops, with their students
    private static void printWorkshops(Collection<Workshop> workshops) {
        for (Workshop w : workshops) {
            System.out.println(w.name + " session one (" + w.studentsBySession.get(0).size() + " students):");
            System.out.println(w.studentsBySession.get(0).stream().map(s -> s.name).collect(Collectors.joining(", ")));
            System.out.println();
            System.out.println();
            System.out.println(w.name + " session two (" + w.studentsBySession.get(1).size() + " students):");
            System.out.println(w.studentsBySession.get(1).stream().map(s -> s.name).collect(Collectors.joining(", ")));
            System.out.println();
            System.out.println();
            System.out.println(w.name + " session three (" + w.studentsBySession.get(2).size() + " students):");
            System.out.println(w.studentsBySession.get(2).stream().map(s -> s.name).collect(Collectors.joining(", ")));
            System.out.println();
            System.out.println();
        }
    }

    // A helper function for tests that determines whether an allocation of students is valid.
    // If the allocation is valid, returns the total number of students in workshops that they want.
    // If the allocation is invalid, returns -1 and prints out the issue.
    private static int isValid(List<Student> students, List<Workshop> workshops) {
        int fewestPreferred = Integer.MAX_VALUE;
        int happiness = 0;
        for (Student s : students) {
            int preferredCount = 0;
            if (s.sessions[0] == null || s.sessions[1] == null || s.sessions[2] == null) {
                System.out.println("There is a student assigned to fewer than 3 sessions:");
                System.out.println(s.name);
                return -1;
            }
            Set<Category> categories = new HashSet<>();
            for (Workshop w : s.sessions) {
                if (s.preferences.contains(w)) {
                    preferredCount++;
                    happiness++;
                }
                if (categories.contains(w.category)) {
                    System.out.println("There is a student in multiple sessions of the same type:");
                    System.out.println(s.name);
                    System.out.println(w.category);
                    return -1;
                }
                categories.add(w.category);
            }
            if (!s.preferences.isEmpty()) {
                fewestPreferred = Math.min(fewestPreferred, preferredCount);
            }
        }
        int maxSize = (int) Math.ceil((double)students.size() / (double)workshops.size());
        for (Workshop w : workshops) {
            if(w.studentsBySession.stream().anyMatch((s) -> s.size() > maxSize)) {
                System.out.println("The students are not evenly distributed. " + w.name + " has too many students.");
                return -1;
            }
        }

        if (fewestPreferred == 0) {
            System.out.println("There are students who gave preferences and received none");
            return -1;
        }
        return happiness;
    }
}


class Student {
    // There are three sessions of workshops, and each student must take a different Category
    // of workshop for each session.
    Workshop[] sessions = new Workshop[3];

    // A set of 5 Workshops that the student is interested in (unranked)
    Set<Workshop> preferences = new HashSet<>();
    String name;

    public Student(String name, Set<Workshop> preferences) {
        this.name = name;
        this.preferences = preferences;
    }

    public Student(String name, Workshop pref1, Workshop pref2, Workshop pref3, Workshop pref4, Workshop pref5) {
        this.name = name;
        this.preferences.add(pref1);
        this.preferences.add(pref2);
        this.preferences.add(pref3);
        this.preferences.add(pref4);
        this.preferences.add(pref5);
    }

    // Determines whether the student currently has a session of the given Category
    public boolean hasSessionOfCategory(Category c) {
        return Arrays.stream(sessions).anyMatch((s) -> s != null && s.category == c);
    }

    // Adds the student to the workshop's list and vice-versa, for the given session
    // index (time period).
    public void assignToWorkshop(Workshop w, int sessionIndex) {
        w.studentsBySession.get(sessionIndex).add(this);
        sessions[sessionIndex] = w;
    }
}

class Workshop {
    List<Set<Student>> studentsBySession = new ArrayList<>();
    Category category;
    String name;

    public Workshop(String name, String category) {
        this.name = name;
        switch (category) {
            case "life skills" -> this.category = Category.LIFE_SKILL;
            case "mental health" -> this.category = Category.MENTAL_HEALTH;
            case "physical health" -> this.category = Category.PHYSICAL_HEALTH;
            case "relationships" -> this.category = Category.RELATIONSHIPS;
            default -> this.category = Category.UNKNOWN;
        }
        initStudentLists();
    }

    public Workshop(String name, Category category) {
        this.name = name;
        this.category = category;
        initStudentLists();
    }

    private void initStudentLists() {
        studentsBySession.add(new HashSet<>());
        studentsBySession.add(new HashSet<>());
        studentsBySession.add(new HashSet<>());
    }
}

enum Category {
    MENTAL_HEALTH, PHYSICAL_HEALTH, LIFE_SKILL, RELATIONSHIPS, UNKNOWN
}
