/**
 * Global type definitions for CodeTED frontend.
 *
 * These types define the standard contract between the frontend and the
 * Spring Boot backend REST API. All API responses must conform to these shapes.
 */

// ---------------------------------------------------------------------------
// API Response Wrappers
// ---------------------------------------------------------------------------

/**
 * Standard successful API response wrapper.
 * Matches the backend {@code ApiResponse<T>} generic class.
 */
export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
  timestamp: string; // ISO-8601
}

/**
 * Standard paginated API response wrapper.
 * Used for list endpoints that support pagination.
 */
export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number; // current page (0-indexed)
  first: boolean;
  last: boolean;
  empty: boolean;
}

/**
 * Standard API error shape returned by the backend's GlobalExceptionHandler.
 */
export interface ApiError {
  success: false;
  message: string;
  errors?: Record<string, string>; // field-level validation errors
  status: number;
  timestamp: string; // ISO-8601
}

// ---------------------------------------------------------------------------
// Utility Types
// ---------------------------------------------------------------------------

/** Represents a value that might not yet be loaded. */
export type Nullable<T> = T | null;

/** Represents an entity with a numeric primary key. */
export interface Identifiable {
  id: number;
}

/** Timestamps common to all persisted entities. */
export interface Auditable {
  createdAt: string; // ISO-8601
  updatedAt: string; // ISO-8601
}

/** Combines identity and audit fields — mirrors BaseEntity in the backend. */
export interface BaseEntity extends Identifiable, Auditable {}

export interface UserProfile {
  id: number;
  publicId: string;
  username: string;
  fullName: string | null;
  email: string;
  role: string;
  status: string;
  roles: string[];
  customerPublicId: string | null;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  tokenType: string;
  expiresIn: number;
  user: UserProfile;
}

export interface DashboardSummary {
  totalUsers: number;
  activeSessions: number;
  publishedInsights: number;
  enabledModules: number;
}

export interface BlogPost {
  id: string;
  publicId: string;
  title: string;
  author: string;
  excerpt: string;
  slug: string;
  content: string | null;
  featured: boolean;
  published: boolean;
}

export interface ServiceOffering {
  id: number;
  publicId: string;
  title: string;
  slug: string;
  summary: string;
  description: string | null;
  featured: boolean;
  active: boolean;
}

export interface PortfolioItem {
  id: number;
  publicId: string;
  title: string;
  slug: string;
  category: string;
  summary: string;
  challenge: string | null;
  solution: string | null;
  result: string | null;
  featured: boolean;
  published: boolean;
}

export interface Customer {
  id: number;
  publicId: string;
  fullName: string;
  email: string | null;
  phone: string | null;
  company: string | null;
  status: string;
  notes: string | null;
}

export interface Project {
  id: number;
  publicId: string;
  customerPublicId: string;
  customerName: string;
  name: string;
  description: string | null;
  status: string;
  startDate: string | null;
  endDate: string | null;
}

export interface Quote {
  id: number;
  publicId: string;
  customerPublicId: string;
  customerName: string;
  title: string;
  description: string | null;
  status: string;
  totalAmount: number;
}

export interface UserSummary {
  publicId: string;
  username: string;
  fullName: string;
  email: string;
  status: string;
  primaryRole: string;
  roles: string[];
  customerPublicId: string | null;
}

export interface Permission {
  publicId: string;
  code: string;
  module: string;
  description: string | null;
}

export interface Role {
  publicId: string;
  name: string;
  description: string | null;
  systemRole: boolean;
  permissions: Permission[];
}

export interface ContactLead {
  id: number;
  publicId: string;
  fullName: string;
  email: string;
  phone: string | null;
  company: string | null;
  message: string;
  source: string;
  status: string;
}

export interface ClientPortal {
  customerName: string;
  email: string | null;
  company: string | null;
  projects: Project[];
  quotes: Quote[];
}

// ---------------------------------------------------------------------------
// Pagination
// ---------------------------------------------------------------------------

/** Query parameters for paginated requests. */
export interface PageParams {
  page?: number;
  size?: number;
  sort?: string;
  direction?: 'ASC' | 'DESC';
}
