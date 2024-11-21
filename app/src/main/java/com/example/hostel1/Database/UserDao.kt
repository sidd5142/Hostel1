//package com.example.hostel1.Database
//
//import UserEntity
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//
//@Dao
//interface UserDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(users: List<UserEntity>)
//
//    @Query("SELECT * FROM users")
//    suspend fun getAllUsers(): List<UserEntity>
//}
