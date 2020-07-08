import axios from 'axios';

export async function findDuplicates() {
  return (await axios.get('/api/finder')).data;
}
