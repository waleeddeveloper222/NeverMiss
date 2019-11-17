package com.waleed.nevermiss.Repo

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.waleed.nevermiss.model.User
import com.waleed.nevermiss.ui.fragment.profile.ProfileViewModel
import com.waleed.nevermiss.ui.sign.SignViewModel


class FireBaseRepo {

    private val TAG = "FireBaseRepo"

    var mAuth: FirebaseAuth
    lateinit var signViewModel: SignViewModel
    lateinit var profileViewModel: ProfileViewModel


    constructor() {
        mAuth = FirebaseAuth.getInstance()
    }

    constructor(signViewModel: SignViewModel) {
        mAuth = FirebaseAuth.getInstance()
        this.signViewModel = signViewModel
    }

    constructor(profileViewModel: ProfileViewModel) {
        mAuth = FirebaseAuth.getInstance()
        this.profileViewModel = profileViewModel
    }


    // The user's ID, unique to the Firebase project. Do NOT use this value to
    // authenticate with your backend server, if you have one. Use
    // FirebaseUser.getIdToken() instead.

    val getUserData: User?
        get() {
            var user: User? = null

            val firebaseUser = FirebaseAuth.getInstance().currentUser

            if (firebaseUser != null) {
                val uid = firebaseUser.uid
                val name = firebaseUser.displayName
                val email = firebaseUser.email
                val photoUrl = firebaseUser.photoUrl


                Log.d(TAG, "firebaseUser:uid =  $uid")
                Log.d(TAG, "firebaseUser:name =  " + name!!)
                Log.d(TAG, "firebaseUser:email =  " + email!!)
                //  Log.d(TAG, "firebaseUser:photoUrl =  " + photoUrl!!)


                user = User(uid, email, name)
            }

            return user
        }


    fun login(email: String, password: String) {

        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //  Log.d(TAG, "signInWithEmail:success");
                    val firebaseUser = mAuth!!.currentUser
                    // updateUI(firebaseUser);

                    if (firebaseUser != null) {
                        // Name, email address, and profile photo Url
                        val name = firebaseUser.displayName
                        val email = firebaseUser.email
                        // Check if user's email is verified
                        //boolean emailVerified = firebaseUser.isEmailVerified();

                        // The user's ID, unique to the Firebase project. Do NOT use this value to
                        // authenticate with your backend server, if you have one. Use
                        // FirebaseUser.getIdToken() instead.
                        // String uid = firebaseUser.getUid();

                    }

                    Log.d(TAG, "signInWithEmail:success")
                    signViewModel!!.sendMessage()


                } else {
                    // If sign in fails, display a myMessage to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    //updateUI(null);

                    signViewModel!!.sendError(task.exception!!.message!!)
                }
            }

    }

    fun register(userName: String, email: String, password: String) {

        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val firebaseUser = mAuth!!.currentUser
                    // updateUI(firebaseUser);

                    if (firebaseUser != null) {
                        // Name, email address, and profile photo Url
                        val name = firebaseUser.displayName
                        val email = firebaseUser.email

                        // Check if user's email is verified
                        // boolean emailVerified = firebaseUser.isEmailVerified();

                        // The user's ID, unique to the Firebase project. Do NOT use this value to
                        // authenticate with your backend server, if you have one. Use
                        // FirebaseUser.getIdToken() instead.
                        // String uid = firebaseUser.getUid();

                    }

                    // signViewModel.sendMessage();

                    updateUsername(userName)
                } else {
                    // If sign in fails, display a myMessage to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    // updateUI(null);
                    task.exception!!.message?.let { signViewModel!!.sendError(it) }
                }
            }
    }

    fun sendEmail(email: String) {

        mAuth!!.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                    signViewModel!!.sendMessage()
                } else {
                    signViewModel!!.sendError(task.exception!!.message!!)
                }
            }
    }

    fun updateUsername(userName: String) {

        val user = FirebaseAuth.getInstance().currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(userName)
            // .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
            .build()

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    if (signViewModel != null) {
                        signViewModel!!.sendMessage()
                    } else {
                        profileViewModel.sendMessage("username updated")
                    }
                } else {
                    if (signViewModel != null) {
                        task.exception!!.message?.let { signViewModel!!.sendError(it) }
                    } else {
                        profileViewModel.sendMessage("Error, Try Again")
                    }
                }
            }


    }

    fun updateEmail(email: String) {
        val user = FirebaseAuth.getInstance().currentUser
        user!!.updateEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, " email address updated.")
                    profileViewModel.sendMessage("email updated")
                } else {
                    profileViewModel.sendMessage("Error, Try Again")
                }
            }


    }

    fun updatePassword(password: String) {

        val user = FirebaseAuth.getInstance().currentUser

        user!!.updatePassword(password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "password updated.")

                    profileViewModel.sendMessage("password updated")
                } else {
                    profileViewModel.sendMessage("Error, Try Again")
                }
            }

    }

    fun signOut() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            FirebaseAuth.getInstance().signOut()
            signViewModel!!.sendMessage()
        } else {

        }

    }

    fun deleteAccount() {
        val user = FirebaseAuth.getInstance().currentUser

        user!!.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User account deleted.")
                }
            }
    }


}
