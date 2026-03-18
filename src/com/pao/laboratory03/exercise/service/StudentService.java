package com.pao.laboratory03.exercise.service;

import com.pao.laboratory03.exercise.exception.StudentNotFoundException;
import com.pao.laboratory03.exercise.model.Student;
import com.pao.laboratory03.exercise.model.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentService {
    private static StudentService instance;
    private final List<Student> students;

    private StudentService() {
        this.students = new ArrayList<>();
    }

    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }

    public void addStudent(String name, int age) {
        for (Student student : students) {
            if (student.getName().equals(name)) {
                throw new RuntimeException("Un student cu acest nume exista deja");
            }
        }
        students.add(new Student(name, age));
    }

    public Student findByName(String name) {
        for (Student student : students) {
            if (student.getName().equals(name)) {
                return student;
            }
        }
        throw new StudentNotFoundException("Studentul  " + name + " nu exista");
    }

    public void addGrade(String studentName, Subject subject, double grade) {
        Student student = findByName(studentName);
        student.addGrade(subject, grade);
    }

    public void printAllStudents() {
        if (students.isEmpty()) {
            System.out.println("Nu exista studenti inregistrati");
            return;
        }
        for (Student student : students) {
            System.out.println(student + " Note: " + student.getGrades());
        }
    }

    public void printTopStudents() {
        if (students.isEmpty()) {
            System.out.println("Nu exista studenti inregistrati.");
            return;
        }
        List<Student> sortedStudents = new ArrayList<>(students);
        sortedStudents.sort((s1, s2) -> Double.compare(s2.getAverage(), s1.getAverage()));

        System.out.println();
        for (Student student : sortedStudents) {
            System.out.println(student);
        }
    }

    public Map<Subject, Double> getAveragePerSubject() {
        Map<Subject, Double> sumMap = new HashMap<>();
        Map<Subject, Integer> countMap = new HashMap<>();

        for (Student student : students) {
            for (Map.Entry<Subject, Double> entry : student.getGrades().entrySet()) {
                Subject subject = entry.getKey();
                double grade = entry.getValue();

                sumMap.put(subject, sumMap.getOrDefault(subject, 0.0) + grade);
                countMap.put(subject, countMap.getOrDefault(subject, 0) + 1);
            }
        }

        Map<Subject, Double> averageMap = new HashMap<>();
        for (Subject subject : sumMap.keySet()) {
            averageMap.put(subject, sumMap.get(subject) / countMap.get(subject));
        }

        return averageMap;
    }
}