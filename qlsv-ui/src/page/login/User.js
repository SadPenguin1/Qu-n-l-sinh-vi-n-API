import React, { useEffect, useState } from "react";
import { searchUserAPI,createUserAPI,updateUserAPI,deleteUserAPI } from "../../service/user.service";
import { Box, Button, FormControl, InputLabel, MenuItem, Select, Table, TableBody, TableCell, TableHead, TableRow, TextField } from "@mui/material";
import { Delete, Send, Update } from "@mui/icons-material";


export function User() {
    
    let [userArr, setUserArr] = useState([])
    let [data,setData] = useState({
        "value": "%%",
        "page": 0,
        "size": 10
    })

    useEffect(() => {
        const readData = async () => {
            // async request.
            const response = await searchUserAPI(data);
            setUserArr(response.data)
            if (response) {
                return;
            }
        }
        readData();
 
    }, [data])

    let handleTextChange = (e)=>{
        setData({...data,[e.target.name]:e.target.value})
    }

    let [userDTO, setUserDTO] = useState({
        id: "0",
        username: "dung",
        password: "dung",
        enabled: true,
        email: "dung@gmail.com",
        role:[]
        
      });

      let handleChangeUser = (e) => {
        setUserDTO({ ...userDTO, [e.target.name]: e.target.value });
      };
    
    let createUser = async () => {
        try {
          console.log(userDTO);
          let resp = await createUserAPI(userDTO); 
          console.log(resp);
          console.log("Tạo user thành công");
        } catch (err) {
          console.log(err);
        }
      };

      let updateUser = async () => {
        try {
          console.log(userDTO);
          let resp = await updateUserAPI(userDTO); 
          console.log("Update data: ", resp.data);
          console.log("Update user thành công");
          setUserArr(resp.data.data);
        } catch (err) {
          console.log(err);
        }
      };

      let deleteUser = async (id) => {
        try {
          let yes = window.confirm("Are you sure want to delete this item ?");
          let resp = await deleteUserAPI(id); //await dùng trong hàm async em queen
          let newArray = userArr.filter(function (item) {
            //Tương đương ((item) => item.id !== id);
            return item.id !== id;
          });
          console.log("Delete user thành công");
          setUserArr(newArray.data.data);
          // setUserArray(resp.data.data);
        } catch (err) {
          console.log(err);
        }
      }

    return (
        <div>
            
      <th>Create User</th>
      <form>
        
      {/*  <input name="id" onChange={handleChangeUser} placeholder="ID.." />*/} 
        <TextField  label="Username" name="username" onChange={handleChangeUser} variant="standard" placeholder="Username.."/>
        <TextField  label="Password" name="password" onChange={handleChangeUser} variant="standard" placeholder="Password.."/>
        <TextField  label="Email" name="email" onChange={handleChangeUser}  variant="standard" placeholder="Email.." />
        <TextField  label="Role" name="role" onChange={handleChangeUser}  variant="standard" placeholder="role.." />
        { /* <input name="role" onChange={handleChangeUser} placeholder="Role.." /> */}

       
        <Button  onClick={createUser}  startIcon={<Send/>} variant="outlined">Save </Button>
       
        
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
                    <TableCell align="center">Username</TableCell>
                    <TableCell align="center">Password</TableCell>
                    <TableCell align="center">Enable</TableCell>
                    <TableCell align="center">Email</TableCell>
                  
                    <TableCell align="center">Option</TableCell>
                </TableRow>
                </TableHead>
                <TableBody>
                {
                    userArr.map(item => {
                        return (
                            <TableRow key={item.id}>
                                <TableCell>{item.id}</TableCell>
                                <TableCell>{item.username}</TableCell>
                                <TableCell>{item.password}</TableCell>
                                <TableCell>{item.enable}</TableCell>  
                                <TableCell>{item.email}</TableCell> 
                                                    

                                
                                <TableCell>
                                  <Button  onClick={() => deleteUser(item.id)} startIcon={<Delete/>} variant="outlined">Delete </Button>
                                  <Button  onClick={() => updateUser(item.id)} startIcon={<Update/>} variant="outlined">Update</Button>
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