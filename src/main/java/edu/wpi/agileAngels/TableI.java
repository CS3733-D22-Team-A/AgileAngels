package edu.wpi.agileAngels;

import java.util.List;

public interface TableI {
    boolean add(Object obj);
    boolean delete(String str);
    boolean update(String str, List list);
    boolean createTable();
    boolean dropTable();
}
