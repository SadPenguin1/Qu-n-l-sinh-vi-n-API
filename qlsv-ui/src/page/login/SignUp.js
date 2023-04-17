import React from 'react'
import { Grid, Paper, Avatar, Typography, TextField, Button, FormControl, FormControlLabel, RadioGroup, Checkbox, FormLabel, Radio } from '@mui/material'
import { Link } from 'react-router-dom'
const Signup = () => {
    const avatarStyle = { backgroundColor: '#1bbd7e' }
    const marginTop = { marginTop: 5 }
    return (
        <Grid>
            <Paper elevation={20} style={{ padding: '30px 20px', width: 300, margin: "20px auto"}}>
                <Grid align='center'>
                    <Avatar style={avatarStyle}>
                    </Avatar>
                    <h2 >Sign Up</h2>
                    <Typography variant='caption' gutterBottom>Please fill this form to create an account !</Typography>
                </Grid>
                <form>
                    <TextField margin="normal" fullWidth placeholder="Enter your name" />
                    <TextField margin="normal" fullWidth  placeholder="Enter your email" />
                    <FormControl component="fieldset" style={marginTop}>
                        <FormLabel component="legend">Gender</FormLabel>
                        <RadioGroup aria-label="gender" name="gender" style={{ display: 'initial' }}>
                            <FormControlLabel value="female" control={<Radio />} label="Female" />
                            <FormControlLabel value="male" control={<Radio />} label="Male" />
                        </RadioGroup>
                    </FormControl>
                    <TextField margin="normal"fullWidth  placeholder="Enter your phone number" />
                    <TextField margin="normal" fullWidth  placeholder="Enter your password"/>
                    <TextField margin="normal" fullWidth  placeholder="Confirm your password"/>
                    <FormControlLabel
                        control={<Checkbox name="checkedA" />}
                        label="I accept the terms and conditions."
                    />
                    <Link to="/" style={{textDecoration:'none'}}>
                    <Button type="submit" variant="contained" color="warning" sx={{marginTop:3,borderRadius:3}}>SignUp</Button>
                    </Link>
                </form>
            </Paper>
        </Grid>
    )
}

export default Signup;