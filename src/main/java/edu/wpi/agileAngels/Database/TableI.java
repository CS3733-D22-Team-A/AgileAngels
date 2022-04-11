package edu.wpi.agileAngels.Database;

public interface TableI {
  boolean add(Object obj);

  boolean delete(String str);

  boolean update(Object obj);

  boolean createTable();

  boolean dropTable();
}
