package org.yb.board.controller;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@Log4j2
public class CategoryController {

    private org.yb.board.service.impl.CategoryServiceImpl categoryService;

    public CategoryController(org.yb.board.service.impl.CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @org.yb.board.aop.LoginCheck(type = org.yb.board.aop.LoginCheck.UserType.ADMIN)
    public void registerCategory(String accountId, @RequestBody org.yb.board.dto.CategoryDTO categoryDTO) {
        categoryService.register(accountId, categoryDTO);
    }

    @PatchMapping("{categoryId}")
    @org.yb.board.aop.LoginCheck(type = org.yb.board.aop.LoginCheck.UserType.ADMIN)
    public void updateCategories(String accountId,
                                 @PathVariable(name = "categoryId") int categoryId,
                                 @RequestBody CategoryRequest categoryRequest) {
        org.yb.board.dto.CategoryDTO categoryDTO = new org.yb.board.dto.CategoryDTO(categoryId, categoryRequest.getName(), org.yb.board.dto.CategoryDTO.SortStatus.NEWEST,10,1);
        categoryService.update(categoryDTO);
    }

    @DeleteMapping("{categoryId}")
    @org.yb.board.aop.LoginCheck(type = org.yb.board.aop.LoginCheck.UserType.ADMIN)
    public void updateCategories(String accountId,
                                 @PathVariable(name = "categoryId") int categoryId) {
        categoryService.delete(categoryId);
    }

    // -------------- request 객체 --------------

    @Setter
    @Getter
    private static class CategoryRequest {
        private int id;
        private String name;
    }

}