import { useNavigate } from "react-router-dom"

import * as Yup from "yup"
import { createUserAPI } from "../../../service/user.service"
import { Button, Grid, Stack, TextField, Typography } from "@mui/material"
import { Send } from "@mui/icons-material"
import {yupResolver} from '@hookform/resolvers'
import { useForm } from "react-hook-form"

export default function UserCreate(){
     
    let navigate = useNavigate()

    const NewItemSchema = Yup.object().shape({
        name: Yup.string().required("Required,please enter.")
    })

    const create = async (user) => {
        let {code} = await createUserAPI(user)
        navigate("/dashboard/user")
        if(code === 200){
            navigate("/dashboard/user")
        }else{
            console.log(code)
        }

    const { register,handleSubmit,formState: {errors}} = useForm({
        resolver: yupResolver(NewItemSchema)
    })    
    }
    return(
        <Grid container alignItems={"center"} justifyContent={"center"}>
            <Grid item xs={12} sm={6}>
                <Stack spacing={3}>
                    <Typography variant="h6">
                        Create User
                    </Typography>
                    <form onSubmit={handleSubmit(create)}>
                        <Stack spacing={3}>
                            <TextField label="name" variant="outlined" size="small" {...register("name")} error={errors.name} helperText={errors.name?.message}/>
                            <Button variant="outlined" type='submit' >Create</Button>
                        </Stack>
                    </form>
                </Stack>
            </Grid>
        </Grid>
    )
}