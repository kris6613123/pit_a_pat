package com.pitapat.pitapatPoint.controller;


import com.pitapat.pitapatPoint.service.FileService;
import com.pitapat.pitapatPoint.vo.CustomerVO;
import com.pitapat.pitapatPoint.vo.FileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping( method = RequestMethod.GET )
public class CtrlFile extends CtrlBase{

    private final FileService fileService;
    private final Environment env;

    @RequestMapping( value = "/image/{id}" )
    public void getImage( HttpServletResponse res, @PathVariable("id") Integer id ) throws Exception {
        FileVO v = fileService.getItemById( id );
        String realFile = env.getProperty("file.save.root") + File.separator + v.getPath() + File.separator + v.getServerName();
        String fileNm = new String( v.getName().getBytes("8859_1"), "UTF-8" );
        String extension = StringUtils.getFilenameExtension( v.getName() );
        BufferedOutputStream out = null;
        InputStream in = null;

        try {
            res.setContentType( "image/" + extension);
            res.setHeader( "Content-Disposition", "inline;filename=" + fileNm );
            File file = new File( realFile );
            if (file.exists()) {
                in = Files.newInputStream( file.toPath() );
                out = new BufferedOutputStream( res.getOutputStream() );
                int len;
                byte[] buf = new byte[1024];
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        } catch (Exception e) {
        } finally {
            if (out != null) {
                out.flush();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
    }

    @ResponseBody
    @RequestMapping( value = {"/image/add"}, method = RequestMethod.POST  )
    public ResponseEntity<Integer> add(@RequestPart( value = "formFile" ) MultipartFile file ) throws IOException {
        log.info("file add : " + file);
        return new ResponseEntity<>( fileService.upload( file, "pat" ), HttpStatus.OK );
    }
}
