package me.bauer.BauerCam.CameraRollCoreMod;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import net.minecraft.launchwrapper.IClassTransformer;

public final class CameraRollInjector implements IClassTransformer {

	private static final String deobfuscatedClass = "net.minecraft.client.renderer.EntityRenderer";

	private static final String deobfuscatedMethod = "orientCamera";
	private static final String obfuscatedMethod = "f";

	@Override
	public byte[] transform(final String obfuscated, final String deobfuscated, final byte[] bytes) {
		if (deobfuscatedClass.equals(deobfuscated)) {
			final ClassReader classReader = new ClassReader(bytes);
			final ClassNode classNode = new ClassNode();
			classReader.accept(classNode, 0);

			final String method = obfuscated.equals(deobfuscated) ? deobfuscatedMethod : obfuscatedMethod;

			for (final MethodNode m : classNode.methods) {
				if (method.equals(m.name) && "(F)V".equals(m.desc)) {
					inject(m.instructions);

					final ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
					classNode.accept(classWriter);
					return classWriter.toByteArray();
				}
			}
		}

		return bytes;
	}

	private void inject(final InsnList instructionSet) {
		final InsnList addition = new InsnList();
		addition.add(new FieldInsnNode(Opcodes.GETSTATIC, "me/bauer/BauerCam/CameraRoll", "roll", "F"));
		addition.add(new LdcInsnNode(Float.valueOf(0)));
		addition.add(new LdcInsnNode(Float.valueOf(0)));
		addition.add(new LdcInsnNode(Float.valueOf(1)));
		addition.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "org/lwjgl/opengl/GL11", "glRotatef", "(FFFF)V", false));
		instructionSet.insert(addition);
	}
}

/**
 * TODO: needs to be done for 1.9
 */

/**
 * Code snippet to do research upon the obfuscated method layer
 */
/**
 * if ("(F)V".equals(m.desc)) { System.out.println(m.name);
 * System.out.println(m.instructions.size()); ListIterator<AbstractInsnNode> i =
 * m.instructions.iterator(); while (i.hasNext()) { AbstractInsnNode node =
 * i.next(); if (node.getOpcode() == Opcodes.INVOKESTATIC) { MethodInsnNode
 * mNode = (MethodInsnNode) node; if (mNode.desc.equals("(FFFF)V")) {
 * System.out.println(mNode.owner); System.out.println(mNode.name); } } } }
 */
