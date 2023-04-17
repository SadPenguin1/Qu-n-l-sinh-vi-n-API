import { createSlice } from "@reduxjs/toolkit";
import { searchCourseAPI } from "../service/course.service";

 const courseSlice = createSlice({
    name: "course",
    initialState: {
        isLoading: false,
        error: null,
        courses:[],
        course: null,
        search:{
            value: "%%",
            page: 0,
            size: 10
        }
        
    },
    reducers: {
        startLoading: (state) => {
            state.isLoading = true;
        },
        setError: (state,action)=>{
            state.isLoading =false
            state.error = action.payload
        },
        setCourses: (state,action)=> {
            console.log(action.payload.data);
            state.courses = action.payload.data
            state.isLoading = false;

        },
        setCourseSearch: (state, action) => {
            state.search = action.payload;//doc gia tri truyen vao
            state.isLoading = false;
        },
        setCourse: (state,action)=> {
            state.course = action.payload.data
            state.isLoading = false
            state.error =null
        },
    }
});

export const {startLoading,setCourseSearch} = courseSlice.actions;

export default courseSlice.reducer;

export const searchCourse =()=> async(dispatch,getState) => {

    dispatch(courseSlice.actions.startLoading())

    const {course} = getState()
    const {search} = course

    const resp = await searchCourseAPI(search)
   // console.log(resp.code)

    if (resp.code === '200')
        dispatch(courseSlice.actions.setCourses(resp))
     else
        dispatch(courseSlice.actions.setError(resp.code))    
    
}