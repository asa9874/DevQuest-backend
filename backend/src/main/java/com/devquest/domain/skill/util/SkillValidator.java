package com.devquest.domain.skill.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.springframework.stereotype.Component;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.skill.model.SkillRequiredSkill;
import com.devquest.domain.skill.repository.MemberSkillRepository;
import com.devquest.domain.skill.repository.SkillRepository;
import com.devquest.domain.skill.repository.SkillRequiredSkillRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SkillValidator {
    private final SkillRepository skillRepository;
    private final MemberSkillRepository memberSkillRepository;
    private final SkillRequiredSkillRepository skillRequiredSkillRepository;
    
    /**
     * 스킬의 소유자인지 확인
     */
    public boolean isSkillOwner(Long skillId, Long memberId) {
        return AuthUtil.isAdmin() || 
                skillRepository.findById(skillId)
                    .map(skill -> skill.getCreater().getId().equals(memberId))
                    .orElse(false);
    }
    
    /**
     * 멤버가 스킬을 보유하고 있는지 확인
     */
    public boolean hasMemberSkill(Long memberId, Long skillId) {
        return AuthUtil.isAdmin() ||
                memberSkillRepository.existsByMemberIdAndSkillId(memberId, skillId);
    }
    
    /**
     * 순환 참조 여부 확인
     * sourceSkillId가 targetSkillId를 직간접적으로 참조하는지 확인
     */
    public boolean hasCircularDependency(Long sourceSkillId, Long targetSkillId) {
        Map<Long, List<Long>> skillDependencies = getAllSkillDependencies();
        
        Set<Long> visited = new HashSet<>();
        Stack<Long> stack = new Stack<>();
        
        stack.push(sourceSkillId);
        
        while (!stack.isEmpty()) {
            Long currentSkillId = stack.pop();
            
            if (visited.contains(currentSkillId)) {
                continue;
            }
            
            visited.add(currentSkillId);
            
            List<Long> requiredSkillIds = skillDependencies.getOrDefault(currentSkillId, Collections.emptyList());
            
            for (Long requiredSkillId : requiredSkillIds) {
                if (requiredSkillId.equals(targetSkillId)) {
                    return true;
                }
                
                if (!visited.contains(requiredSkillId)) {
                    stack.push(requiredSkillId);
                }
            }
        }
        
        return false;
    }
    
    /**
     * 모든 스킬 의존성 정보를 가져옴
     */
    private Map<Long, List<Long>> getAllSkillDependencies() {
        List<SkillRequiredSkill> allSkillDependencies = skillRequiredSkillRepository.findAll();
        
        Map<Long, List<Long>> dependencyMap = new HashMap<>();
        
        for (SkillRequiredSkill dependency : allSkillDependencies) {
            Long skillId = dependency.getSkill().getId();
            Long requiredSkillId = dependency.getRequiredSkill().getId();
            
            dependencyMap.computeIfAbsent(skillId, k -> new ArrayList<>()).add(requiredSkillId);
        }
        
        return dependencyMap;
    }
}
