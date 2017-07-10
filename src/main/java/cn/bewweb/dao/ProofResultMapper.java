package cn.bewweb.dao;

import cn.bewweb.entities.ProofResult;

public interface ProofResultMapper {
    int deleteByPrimaryKey(Long proofResultId);

    int insert(ProofResult record);

    int insertSelective(ProofResult record);

    ProofResult selectByPrimaryKey(Long proofResultId);

    int updateByPrimaryKeySelective(ProofResult record);

    int updateByPrimaryKey(ProofResult record);
}