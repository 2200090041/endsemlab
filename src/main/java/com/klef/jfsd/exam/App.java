package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("Hibernate.cfg.xml").build();
        Metadata md = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory sf = md.getSessionFactoryBuilder().build();
        Session session = sf.openSession();
        Transaction transaction;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Insert Department");
            System.out.println("2. Delete Department");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    while (true) {
                        Department department = new Department();
                        System.out.print("Enter Department Name: ");
                        department.setName(sc.next());
                        System.out.print("Enter Location: ");
                        department.setLocation(sc.next());
                        System.out.print("Enter Head of Department (HoD) Name: ");
                        department.setHodName(sc.next());

                        transaction = session.beginTransaction();
                        session.save(department);
                        transaction.commit();
                        System.out.println("Department inserted successfully!");

                        System.out.print("Do you want to insert another department? (yes/no): ");
                        String insertMore = sc.next();
                        if (!insertMore.equalsIgnoreCase("yes")) {
                            break;
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter Department ID to delete: ");
                    int deptId = sc.nextInt();

                    transaction = session.beginTransaction();
                    String hql = "delete from Department where deptId = ?1";
                    int result = session.createQuery(hql).setParameter(1, deptId).executeUpdate();
                    transaction.commit();

                    if (result > 0) {
                        System.out.println("Department with ID " + deptId + " deleted successfully!");
                    } else {
                        System.out.println("No department found with ID " + deptId);
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    sc.close();
                    session.close();
                    sf.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
