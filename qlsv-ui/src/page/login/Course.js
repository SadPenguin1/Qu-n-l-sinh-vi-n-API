import React, { useEffect, useState } from "react";
import { searchCourseAPI,createCourseAPI,updateCourseAPI,deleteCourseAPI } from "../../service/course.service";
import { Box, Button, FormControl, InputLabel, MenuItem, Select, Table, TableBody, TableCell, TableHead, TableRow, TextField } from "@mui/material";
import { Delete, Send, Update } from "@mui/icons-material";

export function Course() {
    
    let [courseArr, setCourseArr] = useState([])
    let [data,setData] = useState({
        "value": "%%",
        "page": 0,
        "size": 10
    })
    useEffect(() => {
        const readData = async () => {
            // async request.
            const response = await searchCourseAPI(data);
            setCourseArr(response.data)
            if (response) {
                return;
            }
        }
        readData();
 
    }, [data])

    let handleTextChange = (e)=>{
        setData({...data,[e.target.name]:e.target.value})
    }

    let [courseDTO, setCourseDTO] = useState({
        id: "",
        name: "Toan",
        
      });

      let handleChangeCourse = (e) => {
        setCourseDTO({ ...courseDTO, [e.target.name]: e.target.value });
      };
    
    let createCourse = async () => {
        try {
          console.log(courseDTO);
          let resp = await createCourseAPI(courseDTO); 
          console.log("Tạo course thành công");
        } catch (err) {
          console.log(err);
        }
      };

      let updateCourse = async () => {
        try {
          console.log(courseDTO);
          let resp = await updateCourseAPI(courseDTO); 
          console.log("Update data: ", resp.data);
          console.log("Update course thành công");
          setCourseArr(resp.data.data);
        } catch (err) {
          console.log(err);
        }
      };

      let deleteCourse = async (id) => {
        try {
          let yes = window.confirm("Are you sure want to delete this item ?");
          let resp = await deleteCourseAPI(id); 
          let newArray = courseArr.filter(function (item) {
           
            return item.id !== id;
          });
          console.log("Delete course thành công");
          setCourseArr(newArray.data.data);
         
        } catch (err) {
          console.log(err);
        }
      }

    return (
        <div>
            
      <th>Create Course</th>
      <form>
       
        <TextField label="name" name="name" onChange={handleChangeCourse} variant="standard" placeholder="course.."/>
       
        
        <Button  onClick={createCourse}  startIcon={<Send/>} variant="outlined">Save </Button>
        
    
          
        <Box
            component="form"
            sx={{'& .MuiTextField-root': { m: 1, width: '25ch' }, }}
            noValidate
            autoComplete="off"
                 >
            <TextField  label="Search" name ="value" onChange={handleTextChange}  variant="standard" placeholder="Search..."></TextField>
          
            <FormControl>
               <InputLabel id="demo-simple-select-label">Size</InputLabel>
                <Select name ="size" onChange={handleTextChange}>
              
                <MenuItem value="10">10</MenuItem>
                <MenuItem value="20">20</MenuItem>
            </Select>
            </FormControl>
            </Box>
            <Table  sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
                <TableHead>
                <TableRow>
                    <TableCell>ID</TableCell>
                    <TableCell align="center">Course</TableCell>
                    
                  
                    <TableCell align="left">Option</TableCell>
                </TableRow>
                </TableHead>
                <TableBody>
                {
                    courseArr.map(item => {
                        return (
                            <TableRow key={item.id}>
                                <TableCell>{item.id}</TableCell>
                                <TableCell>{item.name}</TableCell>
                              

                                
                                <TableCell>
                                  <Button  onClick={() => deleteCourse(item.id)} startIcon={<Delete/>} variant="outlined">Delete </Button>
                                  <Button  onClick={() => updateCourse(item.id)} startIcon={<Update/>} variant="outlined">Update</Button>
                               </TableCell>
                            </TableRow>
                        )
                    })
                }
             </TableBody>   
            </Table>
           </form>
         </div>
    )
 }