package edu.wpi.agileAngels.Database;

import java.util.List;

public interface TableI {
    boolean add(Object obj);
    boolean delete(String str);
    boolean update(Object obj);
    boolean createTable();
    boolean dropTable();
}
