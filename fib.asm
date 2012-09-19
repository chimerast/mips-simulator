main:
        addi $sp, $sp, -4
        sw $ra, 0($sp)
        addi $a0, $zero, 8
        jal fib
        lw $ra, 0($sp)
        addi $sp, $sp, 4
        syscall
        jr $ra
fib:
        addi $sp, $sp, -12
        sw $ra, 0($sp)

		addi $t0, $zero, 1
		addi $t1, $zero, 3
		slt $at, $a0, $t1
		beq $at, $t0, pri

        sw $s0, 4($sp)
        sw $s1, 8($sp)

        add $s0, $zero, $a0

        addi $a0, $s0, -1 
        jal fib

        add $s1, $zero, $v0

        addi $a0, $s0, -2 
        jal fib

        add $v0, $v0, $s1

        lw $s1, 8($sp)
        lw $s0, 4($sp)

        j end

pri:
        addi $v0, $zero, 1
        
end:        
        lw $ra, 0($sp)
        addi $sp, $sp, 12
        jr $ra
