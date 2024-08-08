package com.yb.repository;

import com.yb.entity.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PostRepository extends ReactiveCrudRepository<Post,Long>  , PostCustomRepository{

}
