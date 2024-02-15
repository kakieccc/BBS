import myAxios from "../plugins/myAxios";
import { setCurrentUserState } from "../states/user";

export const getCurrentUser = async () => {
    const res = await myAxios.get('/user/current');
    if(res.data) {
        setCurrentUserState(res.data);
        return res.data;
    }
    return null;
}
