import { configureStore } from "@reduxjs/toolkit";
import userReducer from "./userSlice"; //default
import studentReducer from "./studentSlice"
import roleReducer from "./roleSlice"
import courseReducer from "./courseSlice"

export const store = configureStore({
    reducer: { 
        user: userReducer ,// tra ve doi tuong  initialState
        student: studentReducer,
        role: roleReducer,
        course: courseReducer
    },
});