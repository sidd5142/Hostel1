//package com.example.hostel1.Database
//
//import UserEntity
//import UserRepository
//import android.app.Application
//import android.util.Log
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//
////class UserViewModel(application: Application, private val userRepository: UserRepository) : AndroidViewModel(application) {
////    val allUsers: LiveData<List<UserData1>> = userRepository.allUsers
////    private val _presentCount = MutableLiveData(0)
////    val presentCount: LiveData<Int> get() = _presentCount
////
////    fun fetchUsersFromNetwork() {
////        viewModelScope.launch {
////            userRepository.fetchUsersFromNetwork()
////        }
////    }
////
////    fun incrementPresentCount() {
////        _presentCount.value = (_presentCount.value ?: 0) + 1
////    }
////
////    fun decrementPresentCount() {
////        _presentCount.value = (_presentCount.value ?: 0) - 1
////    }
////}
//
//class UserViewModel(private val repository: UserRepository) : ViewModel() {
//    private val _users = MutableLiveData<List<UserEntity>>()
//    val users: LiveData<List<UserEntity>> = _users
//
//    fun fetchUsers() {
//        viewModelScope.launch {
//            try {
//                val users = repository.getUsersFromApi()
//                _users.postValue(users)
//                Log.d("UserViewModel", "Fetched users from API: $users")
//            } catch (e: Exception) {
//                Log.e("UserViewModel", "Error fetching users from API: ${e.message}")
//                val usersFromDb = repository.getUsersFromDb()
//                _users.postValue(usersFromDb)
//                Log.d("UserViewModel", "Fetched users from DB: $usersFromDb")
//            }
//            Log.d("UserViewModel", "Final users: ${_users.value}")
//        }
//    }
//}
