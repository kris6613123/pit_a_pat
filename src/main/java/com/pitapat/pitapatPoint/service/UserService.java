package com.pitapat.pitapatPoint.service;

import com.pitapat.pitapatPoint.dao.UserDAO;
import com.pitapat.pitapatPoint.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder encoder;

    @Transactional
    public void add( UserVO vo ) {
        vo.setPwd(encoder.encode(vo.getPwd()));
        userDAO.add(vo); }

    @Transactional
    public void del( UserVO vo ) { userDAO.del(vo); }

    public UserVO getItemById( String id ) { return userDAO.getItemById( id ); }

    public List<UserVO> getList() {
        return userDAO.getList();
    }

    public List<UserVO> getAllList() {
        return userDAO.getAllList();
    }

//    public void getPagingList( Model model, Integer pagenum, Integer contentnum, String keyword ) {
//        PagerVO page = new PagerVO();
//        page.calculatePaging( 10, userDAO.getCount( keyword ), pagenum, contentnum );
//        List<UserVO> userList = userDAO.getPagingList(( pagenum - 1 ) * contentnum, contentnum, keyword);
//
//        model.addAttribute("page",page);
//        model.addAttribute("userList", userList);
//    }

    public int getUser() {
        String id = ( (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getUsername();
        return getItemById( id ).getUser();
    }


}
