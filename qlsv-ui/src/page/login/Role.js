import React, { useEffect, useState } from "react";
import { searchRoleAPI,createRoleAPI,updateRoleAPI,deleteRoleAPI } from "../../service/role.service";
import { Box, Button, FormControl, InputLabel, MenuItem, Select, Table, TableBody, TableCell, TableHead, TableRow, TextField } from "@mui/material";
import { Delete, Send, Update } from "@mui/icons-material";


export function Role() {
    
    let [roleArr, setRoleArr] = useState([])
    let [data,setData] = useState({
        "value": "%%",
        "page": 0,
        "size": 10
    })

    useEffect(() => {
        const readData = async () => {
            // async request.
            const response = await searchRoleAPI(data);
            setRoleArr(response.data)
            if (response) {
                return;
            }
        }
        readData();
 
    }, [data])

    let handleTextChange = (e)=>{
        setData({...data,[e.target.name]:e.target.value})
    }

    let [roleDTO, setRoleDTO] = useState({
        id: "0",
        name: "ROLE_MEMBER",
       
        
      });

      let handleChangeRole = (e) => {
        setRoleDTO({ ...roleDTO, [e.target.name]: e.target.value });
      };
    
    let createRole = async () => {
        try {
          console.log(roleDTO);
          let resp = await createRoleAPI(roleDTO); 
          console.log(resp);
          console.log("Tạo role thành công");
        } catch (err) {
          console.log(err);
        }
      };

      let updateRole = async () => {
        try {
          console.log(roleDTO);
          let resp = await updateRoleAPI(roleDTO); 
          console.log("Update data: ", resp.data);
          console.log("Update user thành công");
          setRoleArr(resp.data.data);
        } catch (err) {
          console.log(err);
        }
      };

      let deleteRole = async (id) => {
        try {
          let yes = window.confirm("Are you sure want to delete this item ?");
          let resp = await deleteRoleAPI(id); //await dùng trong hàm async em queen
          let newArray = roleArr.filter(function (item) {
            //Tương đương ((item) => item.id !== id);
            return item.id !== id;
          });
          console.log("Delete user thành công");
          setRoleArr(newArray.data.data);
          // setUserArray(resp.data.data);
        } catch (err) {
          console.log(err);
        }
      }

    return (
        <div>
            
      <th>Create Role</th>
      <form>
        
      {/*  <input name="id" onChange={handleChangeUser} placeholder="ID.." />*/} 
      
        <TextField  label="Role" name="name" onChange={handleChangeRole}  variant="standard" placeholder="role.." />
        { /* <input name="role" onChange={handleChangeUser} placeholder="Role.." /> */}

       
        <Button  onClick={createRole}  startIcon={<Send/>} variant="outlined">Save </Button>
       
        
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
                    <TableCell align="center">Role</TableCell>
                    <TableCell align="center">Option</TableCell>
                </TableRow>
                </TableHead>
                <TableBody>
                {
                    roleArr.map(item => {
                        return (
                            <TableRow key={item.id}>
                                <TableCell>{item.id}</TableCell>
                                <TableCell>{item.name}</TableCell>
                               

                                
                                <TableCell>
                                  <Button  onClick={() => deleteRole(item.id)} startIcon={<Delete/>} variant="outlined">Delete </Button>
                                  <Button  onClick={() => updateRole(item.id)} startIcon={<Update/>} variant="outlined">Update</Button>
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