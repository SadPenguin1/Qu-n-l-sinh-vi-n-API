import axios from 'axios';
import { getSession } from '../context/ProvideAuth'
// ----------------------------------------------------------------------
export const axiosQLSV = axios.create({
  baseURL: 'http://localhost:8820',
  headers:{ Authorization: `Bearer ${getSession()}` },
  timeout: 0,
});

